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

    //Only images has the blue channel set to blue.
    if (fragment_color.b == 1.0) {
        if (u_transparent[index] == 1.0) {
            fragment_color.a = 0.0;
        }
    }

    //Apply the colors remapping.
    fragment_color.r = u_remapping[index];

    gl_FragColor = fragment_color;
}