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
    vec4 col = vec4(0.0, 0.0, 0.0, 1.0);

    //Using deterministic arrays to support old mobile phones.
    if (index == 0) col = u_palette[0];
    else if (index == 1) col = u_palette[1];
    else if (index == 2) col = u_palette[2];
    else if (index == 3) col = u_palette[3];
    else if (index == 4) col = u_palette[4];
    else if (index == 5) col = u_palette[5];
    else if (index == 6) col = u_palette[6];
    else if (index == 7) col = u_palette[7];
    else if (index == 8) col = u_palette[8];
    else if (index == 9) col = u_palette[9];
    else if (index == 10) col = u_palette[10];
    else if (index == 11) col = u_palette[11];
    else if (index == 12) col = u_palette[12];
    else if (index == 13) col = u_palette[13];
    else if (index == 14) col = u_palette[14];
    else if (index == 15) col = u_palette[15];

    col.a = col.a * v_color.a * ta;

    //Convert into grayscale for debugging reasons.
    //col = vec4(float(index) / 15.0);

    gl_FragColor = col;
}