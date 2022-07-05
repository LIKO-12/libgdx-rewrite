attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform mat3 u_transMatrix;
varying vec4 v_color;
varying vec2 v_texCoords;

void main()
{
    v_color = a_color;
    v_color.a = v_color.a * (255.0/254.0);
    v_texCoords = a_texCoord0;

    vec3 v_2d_position = vec3(a_position);
    v_2d_position.z = 1.0;

    vec4 v_transformed = vec4(u_transMatrix * v_2d_position, a_position.w);
    v_transformed.z = a_position.z;

    gl_Position = u_projTrans * v_transformed;
}