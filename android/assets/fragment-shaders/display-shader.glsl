#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec4 u_palette[16];
uniform vec4 static_color;

void main()
{
    //Get the color from the texture.
    vec4 textureColor = texture2D(u_texture, v_texCoords);

    //Convert the red channel value into an integer index value.
    int index = int(textureColor.r * 15.0 + 0.5);

    //Whether this was a transparent pixel or not.
    float ta = float(textureColor.a);

    //Lookup the color in the palette by index.
    vec4 col = u_palette[index];

    col.a = col.a * v_color.a * ta;

    //Convert into grayscale for debugging reasons.
    //col = vec4(float(index) / 15.0);

    gl_FragColor = col;
}