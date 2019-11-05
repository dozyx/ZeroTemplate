package cn.dozyx.template.gl;

import android.opengl.GLES20;

/**
 * Create by timon on 2019/11/4
 **/
public class ShaderUtil {

    public static int load(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
