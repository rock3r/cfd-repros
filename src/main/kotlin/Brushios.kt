import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.singleWindowApplication
import org.intellij.lang.annotations.Language
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder
import org.jetbrains.skia.Shader

fun main() = singleWindowApplication {
    IntUiTheme {
        Column(Modifier.padding(16.dp)) {
            var useSimple by remember { mutableStateOf(false) }
            Box {
                Box(Modifier.fillMaxWidth().padding(top = 100.dp).height(1.dp).background(Color.Black))

                var size by remember { mutableStateOf(Size.Zero) }
                val brush =
                    remember(size) {
                        ShaderBrush(
                            oklabShader(
                                start = Offset.Zero,
                                end = Offset(size.width, 0f),
                                colors = listOf(Color.White.copy(alpha = .5f), Color.Blue),
                                stops = listOf(0f, 1f),
                            )
                        )
                    }

                val simpleBrush =
                    remember(size) {
                        ShaderBrush(simpleOklabShader(Color.White.copy(alpha = .5f), Color.Blue, size))
                    }
                Box(
                    Modifier.fillMaxWidth()
                        .height(200.dp)
                        .background(if (useSimple) simpleBrush else brush)
                        .border(1.dp, Color.DarkGray)
                        .onSizeChanged { size = it.toSize() }
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Using the ${if (useSimple) "simple" else "advanced"} shader")
                Spacer(Modifier.width(20.dp))
                DefaultButton({ useSimple = !useSimple }) {
                    Text(if (!useSimple) "Use Simple" else "Use Advanced")
                }
            }
        }
    }
}

@Language("GLSL")
val sksl =
    """
    |uniform vec2 gradientStart;
    |uniform vec2 gradientEnd;
    |uniform vec4 colors[COLOR_COUNT];
    |uniform float stops[COLOR_COUNT];
    |
    |// sRGB <-> Linear sRGB conversion functions (same as before)
    |vec3 toLinearSrgb(vec3 color) {
    |    vec3 a = color / 12.92;
    |    vec3 b = pow((color + 0.055) / 1.055, vec3(2.4));
    |    return mix(a, b, step(0.04045, color));
    |}
    |
    |vec3 fromLinearSrgb(vec3 color) {
    |    vec3 a = color * 12.92;
    |    vec3 b = 1.055 * pow(color, vec3(1.0/2.4)) - 0.055;
    |    return mix(a, b, step(0.0031308, color));
    |}
    |
    |// OkLab conversion functions (same as before)
    |vec3 OkLabFromLinearSrgb(vec3 linear) {
    |    const mat3 im1 = mat3(0.4121656120, 0.2118591070, 0.0883097947,
    |                          0.5362752080, 0.6807189584, 0.2818474174,
    |                          0.0514575653, 0.1074065790, 0.6302613616);
    |    const mat3 im2 = mat3(+0.2104542553, +1.9779984951, +0.0259040371,
    |                          +0.7936177850, -2.4285922050, +0.7827717662,
    |                          -0.0040720468, +0.4505937099, -0.8086757660);
    |    vec3 lms = im1 * linear;
    |    return im2 * (sign(lms) * pow(abs(lms), vec3(1.0/3.0)));
    |}
    |
    |vec3 LinearSrgbFromOkLab(vec3 oklab) {
    |    const mat3 m1 = mat3(+1.000000000, +1.000000000, +1.000000000,
    |                         +0.396337777, -0.105561346, -0.089484178,
    |                         +0.215803757, -0.063854173, -1.291485548);
    |    const mat3 m2 = mat3(+4.076724529, -1.268143773, -0.004111989,
    |                         -3.307216883, +2.609332323, -0.703476310,
    |                         +0.230759054, -0.341134429, +1.706862569);
    |    vec3 lms = m1 * oklab;
    |    return m2 * (lms * lms * lms);
    |}
    |
    |vec4 main(vec2 fragCoord) {
    |    // Project fragment coordinate onto the gradient line to find the interpolation factor 't'
    |    vec2 line = gradientEnd - gradientStart;
    |    vec2 fragVec = fragCoord - gradientStart;
    |    float t = dot(fragVec, line) / dot(line, line);
    |    t = clamp(t, 0.0, 1.0);
    |
    |    // Find the correct color segment for the given 't'
    |    vec4 startColor = colors[0];
    |    vec4 endColor = colors[0];
    |    float startStop = stops[0];
    |    float endStop = stops[0];
    |
    |    // Find the two colors and stops we are between
    |    for (int i = 1; i < COLOR_COUNT; ++i) {
    |        if (t <= stops[i]) {
    |            startColor = colors[i - 1];
    |            endColor = colors[i];
    |            startStop = stops[i - 1];
    |            endStop = stops[i];
    |            break;
    |        }
    |    }
    |
    |    // Renormalize 't' to the range [0, 1] for the current segment
    |    float segmentT = 0.0;
    |    if (endStop > startStop) {
    |        segmentT = (t - startStop) / (endStop - startStop);
    |    }
    |    
    |    // Perform interpolation in OkLab color space
    |    vec3 start = OkLabFromLinearSrgb(toLinearSrgb(startColor.rgb));
    |    vec3 end = OkLabFromLinearSrgb(toLinearSrgb(endColor.rgb));
    |
    |    vec3 gradient = fromLinearSrgb(LinearSrgbFromOkLab(mix(start, end, segmentT)));
    |    float alpha = mix(startColor.a, endColor.a, segmentT);
    |
    |    return vec4(gradient, alpha);
    |}
    """
        .trimMargin()

fun oklabShader(start: Offset, end: Offset, colors: List<Color>, stops: List<Float>): Shader {
    check(colors.size == stops.size) { "Colors and stops must have the same size" }

    val colorFloats = colors.flatMap { it.toFloatArray().toList() }.toFloatArray()
    val stopFloats = FloatArray(colors.size)
    stops.forEachIndexed { index, stop -> stopFloats[index] = stop }

    val builder =
        RuntimeShaderBuilder(
            RuntimeEffect.makeForShader(sksl.replace("COLOR_COUNT", colors.size.toString()))
        )
    builder.uniform("gradientStart", start.x, start.y)
    builder.uniform("gradientEnd", end.x, end.y)
    builder.uniform("colors", colorFloats)
    builder.uniform("stops", stopFloats)
    return builder.makeShader()
}

@Language("GLSL")
val simpleSksl =
    """
    |uniform vec4 startColor;
    |uniform vec4 endColor;
    |uniform vec2 size;
    |
    |// sRGB to Linear sRGB
    |// https://en.wikipedia.org/wiki/SRGB#From_sRGB_to_CIE_XYZ
    |vec3 toLinearSrgb(vec3 color) {
    |    vec3 a = color / 12.92;
    |    vec3 b = pow((color + 0.055) / 1.055, vec3(2.4));
    |    return mix(a, b, step(0.04045, color));
    |}
    |
    |// Linear sRGB to sRGB
    |// https://en.wikipedia.org/wiki/SRGB#From_CIE_XYZ_to_sRGB
    |vec3 fromLinearSrgb(vec3 color) {
    |    vec3 a = color * 12.92;
    |    vec3 b = 1.055 * pow(color, vec3(1.0/2.4)) - 0.055;
    |    return mix(a, b, step(0.0031308, color));
    |}
    |
    |vec3 OkLabFromLinearSrgb(vec3 linear) {
    |    const mat3 im1 = mat3(0.4121656120, 0.2118591070, 0.0883097947,
    |                          0.5362752080, 0.6807189584, 0.2818474174,
    |                          0.0514575653, 0.1074065790, 0.6302613616);
    |    const mat3 im2 = mat3(+0.2104542553, +1.9779984951, +0.0259040371,
    |                          +0.7936177850, -2.4285922050, +0.7827717662,
    |                          -0.0040720468, +0.4505937099, -0.8086757660);
    |    vec3 lms = im1 * linear;
    |    return im2 * (sign(lms) * pow(abs(lms), vec3(1.0/3.0)));
    |}
    |
    |vec3 LinearSrgbFromOkLab(vec3 oklab) {
    |    const mat3 m1 = mat3(+1.000000000, +1.000000000, +1.000000000,
    |                         +0.396337777, -0.105561346, -0.089484178,
    |                         +0.215803757, -0.063854173, -1.291485548);
    |
    |    const mat3 m2 = mat3(+4.076724529, -1.268143773, -0.004111989,
    |                         -3.307216883, +2.609332323, -0.703476310,
    |                         +0.230759054, -0.341134429, +1.706862569);
    |    vec3 lms = m1 * oklab;
    |    return m2 * (lms * lms * lms);
    |}
    |
    |vec4 main(vec2 fragCoord) {
    |    vec3 start = OkLabFromLinearSrgb(toLinearSrgb(startColor.rgb));
    |    vec3 end = OkLabFromLinearSrgb(toLinearSrgb(endColor.rgb));
    |
    |    float scale = fragCoord.x / size.x;
    |
    |    vec3 gradient = fromLinearSrgb(LinearSrgbFromOkLab(mix(start, end, scale)));
    |
    |    return vec4(
    |        gradient,
    |        mix(startColor.a, endColor.a, scale)
    |    );
    |}
    """
        .trimMargin()

fun simpleOklabShader(startColor: Color, endColor: Color, size: Size): Shader {
    val effect = RuntimeEffect.makeForShader(simpleSksl)
    val builder = RuntimeShaderBuilder(effect)

    // Set uniforms with names that match the GLSL code
    builder.uniform("startColor", startColor.toFloatArray())
    builder.uniform("endColor", endColor.toFloatArray())
    builder.uniform("size", floatArrayOf(size.width, size.height))

    return builder.makeShader()
}

private fun Color.toFloatArray() = floatArrayOf(red, green, blue, alpha)
