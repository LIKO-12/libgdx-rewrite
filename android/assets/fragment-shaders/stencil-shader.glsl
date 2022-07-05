#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()
{
    //Discard the fragment if it was with color id 0.
    if (texture2D(u_texture, v_texCoords).r == 0.0) discard;

    //Otherwise affect the stencil, but without modifying the color buffer.
    gl_FragColor = vec4(0.0);
}