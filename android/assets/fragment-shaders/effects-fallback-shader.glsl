#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform float u_transparent[16];
uniform float u_remapping[16];

void main()
{
    //Get the color from the texture.
    vec4 fragment_color = v_color * texture2D(u_texture, v_texCoords);

    //Convert the red channel value into an integer index value.
    int index = int(fragment_color.r * 15.0 + 0.5);

    float f_transparent = 0.0;

    //Using deterministic arrays to support old mobile phones.
    if (index == 0) f_transparent = u_transparent[0];
    else if (index == 1) f_transparent = u_transparent[1];
    else if (index == 2) f_transparent = u_transparent[2];
    else if (index == 3) f_transparent = u_transparent[3];
    else if (index == 4) f_transparent = u_transparent[4];
    else if (index == 5) f_transparent = u_transparent[5];
    else if (index == 6) f_transparent = u_transparent[6];
    else if (index == 7) f_transparent = u_transparent[7];
    else if (index == 8) f_transparent = u_transparent[8];
    else if (index == 9) f_transparent = u_transparent[9];
    else if (index == 10) f_transparent = u_transparent[10];
    else if (index == 11) f_transparent = u_transparent[11];
    else if (index == 12) f_transparent = u_transparent[12];
    else if (index == 13) f_transparent = u_transparent[13];
    else if (index == 14) f_transparent = u_transparent[14];
    else if (index == 15) f_transparent = u_transparent[15];

    //Only images has the blue channel set to blue.
    if (fragment_color.b == 1.0) {
        if (f_transparent == 1.0) {
            fragment_color.a = 0.0;
        }
    }

    //Apply the colors remapping.
    //Using deterministic arrays to support old mobile phones.
    if (index == 0) fragment_color.r = u_remapping[0];
    else if (index == 1) fragment_color.r = u_remapping[1];
    else if (index == 2) fragment_color.r = u_remapping[2];
    else if (index == 3) fragment_color.r = u_remapping[3];
    else if (index == 4) fragment_color.r = u_remapping[4];
    else if (index == 5) fragment_color.r = u_remapping[5];
    else if (index == 6) fragment_color.r = u_remapping[6];
    else if (index == 7) fragment_color.r = u_remapping[7];
    else if (index == 8) fragment_color.r = u_remapping[8];
    else if (index == 9) fragment_color.r = u_remapping[9];
    else if (index == 10) fragment_color.r = u_remapping[10];
    else if (index == 11) fragment_color.r = u_remapping[11];
    else if (index == 12) fragment_color.r = u_remapping[12];
    else if (index == 13) fragment_color.r = u_remapping[13];
    else if (index == 14) fragment_color.r = u_remapping[14];
    else if (index == 15) fragment_color.r = u_remapping[15];

    gl_FragColor = fragment_color;
}