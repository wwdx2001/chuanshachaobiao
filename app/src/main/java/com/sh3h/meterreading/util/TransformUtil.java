package com.sh3h.meterreading.util;


public class TransformUtil {
    //椭球体参数
    private static final double a54 = 6378245.0;
    private static final double a84 = 6378137.0;
    private static final double e84 = Math.sqrt(0.00669437999013);
    private static final double e54 = Math.sqrt(0.00669342162297);
    //private readonly double e542 = 0.00669342162297;
    //private readonly double e842 = 0.00669437999013;
    private static final double arc = 0.017453292519943295;   //Math.PI / 180.0;

    // 起始经度
    private static final double edtD = 120;    // 单位：度
    private static final double edtM = 40;      // 单位：分
    private static final double edtS = 0;      // 单位：秒
    // 七参数
    private static final double edtDX = -546.761779;  // 单位：米
    private static final double edtDY = 878.893699;  // 单位：米
    private static final double edtDZ = 231.79816;  // 单位：米
    private static final double edtRX = -23.215;   // 单位：秒
    private static final double edtRY = 21.798;    // 单位：秒
    private static final double edtRZ = 19.880;   // 单位：秒
    private static final double edtK = -0.000143549282;

    /// <summary>
    /// 高斯投影计算
    /// 地心大地坐标转换为高斯平面坐标
    /// </summary>
    /// <param name="a"></param>
    /// <param name="e1"></param>
    /// <param name="L0">经度起始参数</param>
    /// <param name="B1"></param>
    /// <param name="L1"></param>
    /// <param name="xOut"></param>
    /// <param name="yOut"></param>
    private synchronized static double[] BL2XY(double a, double e1, double L0, double B1, double L1) {
        double num = L1 - L0;
        double num2 = Math.sin(B1);
        double num3 = Math.cos(B1);
        double num4 = Math.pow(e1, 2.0);
        double num5 = Math.pow(e1, 4.0);
        double num6 = Math.pow(e1, 6.0);
        double num7 = Math.pow(e1, 8.0);
        double num8 = Math.pow(e1, 10.0);
        double num9 = Math.tan(B1);
        double num10 = num * num3;
        double num11 = a / Math.sqrt(1.0 - num4 * Math.pow(num2, 2.0));
        double num12 = num4 * Math.pow(num3, 2.0) / (1.0 - num4);
        double num13 = 1.0 + 3.0 * num4 / 4.0;
        num13 += 45.0 * num5 / 64.0;
        num13 += 175.0 * num6 / 256.0;
        num13 += 11025.0 * num7 / 16384.0;
        num13 += 43659.0 * num8 / 65536.0;
        double num14 = 3.0 * num4 / 4.0 + 15.0 * num5 / 16.0 + 525.0 * num6 / 512.0;
        num14 = num14 + 2205.0 * num7 / 2048.0 + 72765.0 * num8 / 65536.0;
        double num15 = 15.0 * num5 / 64.0 + 105.0 * num6 / 256.0 + 2205.0 * num7 / 4096.0;
        num15 += 10395.0 * num8 / 16384.0;
        double num16 = 35.0 * Math.pow(num4, 3.0) / 512.0 + 315.0 * Math.pow(num4, 4.0) / 2048.0 + 31185.0 * Math.pow(num4, 5.0) / 131072.0;
        double num17 = 315.0 * num7 / 16384.0 + 3465.0 * num8 / 65536.0;
        double num18 = 693.0 * num8 / 131072.0;
        double num19 = num13 * (1.0 - num4);
        double num20 = -num14 * (1.0 - num4) / 2.0;
        double num21 = num15 * (1.0 - num4) / 4.0;
        double num22 = -num16 * (1.0 - num4) / 6.0;
        double num23 = num17 * (1.0 - num4) / 8.0;
        double num24 = -1.0 * num18 * a * (1.0 - num4) / 10.0;
        double num25 = num19 * a;
        double num26 = (2.0 * num20 + 4.0 * num21 + 6.0 * num22) * a;
        double num27 = -1.0 * (8.0 * num21 + 32.0 * num22) * a;
        double num28 = (32.0 * num22 + 64.0 * num23) * a;
        double num29 = num25 * B1 + num3 * (num26 * num2 + num27 * Math.pow(num2, 3.0) + num28 * Math.pow(num2, 5.0));
        num29 = num29 + num11 * num9 * num10 * num10 / 2.0 + (5.0 - num9 * num9 + 9.0 * num12 + 4.0 * num12 * num12) * num11 * num9 * Math.pow(num10, 4.0) / 24.0;
        num29 += (61.0 - 58.0 * num9 * num9 + Math.pow(num9, 4.0) + 270.0 * num12 - 330.0 * num12 * num9 * num9) * num11 * num9 * Math.pow(num10, 6.0) / 720.0;
        double num30 = num11 * num10 + (1.0 - num9 * num9 + num12) * num11 * num10 * num10 * num10 / 6.0;
        num30 += (5.0 - 18.0 * num9 * num9 + Math.pow(num9, 4.0) + 14.0 * num12 - 58.0 * num12 * num9 * num9) * num11 * Math.pow(num10, 6.0) / 120.0;
        num30 += 500000.0;
        double[] out = new double[2];
        out[1] = num29; //num29 - 4000000;
        out[0] = num30;
        return out;
    }

    /// <summary>
    /// 高斯投影正算
    /// </summary>
    /// <param name="a"></param>
    /// <param name="f"></param>
    /// <param name="B"></param>
    /// <param name="L"></param>
    /// <param name="L0"></param>
    /// <param name="x"></param>
    /// <param name="y"></param>
    private synchronized static double[] GaosiForward(double a, double f, double B, double L, double L0) {
        double b, c, e1, e2; //短半轴，极点处的子午线曲率半径，第一偏心率，第二偏心率
        double l, W, N;//W为常用辅助函数，N为子午圈曲率半径，M为卯酉圈曲率半径
        double X;//子午线弧长，高斯投影的坐标
        double ita, sb, cb, t;
        double[] m = new double[5];
        double[] n = new double[5];
        double[] out = new double[2];

        //计算一些基本常量
        b = a * (1 - f);
        e1 = Math.sqrt(a * a - b * b) / a;
        e2 = Math.sqrt(a * a - b * b) / b;
        c = a * a / b;
        m[0] = a * (1 - e1 * e1);
        m[1] = 3 * (e1 * e1 * m[0]) / 2.0;
        m[2] = 5 * (e1 * e1 * m[1]) / 4.0;
        m[3] = 7 * (e1 * e1 * m[2]) / 6.0;
        m[4] = 9 * (e1 * e1 * m[3]) / 8.0;
        n[0] = m[0] + m[1] / 2 + 3 * m[2] / 8 + 5 * m[3] / 16 + 35 * m[4] / 128;
        n[1] = m[1] / 2 + m[2] / 2 + 15 * m[3] / 32 + 7 * m[4] / 16;
        n[2] = m[2] / 8 + 3 * m[3] / 16 + 7 * m[4] / 32;
        n[3] = m[3] / 32 + m[4] / 16;
        n[4] = m[4] / 128;

        //由纬度计算子午线弧长
        X = n[0] * B - Math.sin(B) * Math.cos(B) * ((n[1] - n[2] + n[3]) + (2 * n[2] - (16 * n[3] / 3.0))
                * Math.sin(B) * Math.sin(B) + 16 * n[3] * Math.pow(Math.sin(B), 4) / 3.0);

        l = L - L0;//弧度
        ita = e2 * Math.cos(B);
        sb = Math.sin(B);
        cb = Math.cos(B);
        W = Math.sqrt(1 - e1 * e1 * sb * sb);
        N = a / W;
        t = Math.tan(B);
        out[0] = (X + N * sb * cb * l * l / 2 + N * sb * cb * cb * cb * (5 - t * t + 9 * ita * ita + 4 * ita
                * ita * ita * ita) * l * l * l * l / 24 + N * sb * cb * cb * cb * cb * cb * (61 - 58 * t * t + t * t * t * t) * l * l * l * l * l * l / 720);
        out[1] = (N * cb * l + N * cb * cb * cb * (1 - t * t + ita * ita) * l * l * l / 6 + N * cb * cb * cb
                * cb * cb * (5 - 18 * t * t + t * t * t * t + 14 * ita * ita - 58 * ita * ita * t * t) * l * l * l * l * l / 120);
        out[1] = out[1] + 500000;
        return out;
    }

    /// <summary>
    /// 地心大地坐标转换为地心直角坐标
    /// </summary>
    /// <param name="a">椭球长半径</param>
    /// <param name="e">椭球第一偏心率</param>
    /// <param name="B1"></param>
    /// <param name="L1"></param>
    /// <param name="H"></param>
    /// <param name="X"></param>
    /// <param name="Y"></param>
    /// <param name="Z"></param>
    private synchronized static double[] BLH2XYZ(double a, double e, double B1, double L1, double H) {
        double num = Math.cos(B1);
        double num2 = Math.cos(L1);
        double num3 = Math.sin(B1);
        double num4 = Math.sin(L1);
        double num5 = a / Math.sqrt(1.0 - e * e * Math.pow(num3, 2.0));
        double[] out = new double[3];
        out[0] = (num5 + H) * num * num2;
        out[1] = (num5 + H) * num * num4;
        out[2] = (num5 + H - num5 * e * e) * num3;
        return out;
    }

    /// <summary>
    /// 地心直角坐标转换地心大地坐标
    /// </summary>
    /// <param name="a"></param>
    /// <param name="e"></param>
    /// <param name="X"></param>
    /// <param name="Y"></param>
    /// <param name="Z"></param>
    /// <param name="Err"></param>
    /// <param name="B"></param>
    /// <param name="L"></param>
    /// <param name="H"></param>
    private synchronized static double[] XYZ2BLH(double a, double e, double X, double Y, double Z, double Err) {
        int num = 0;
        int num2 = (int) (X * 10.0);
        int num3 = (int) (Y * 10.0);
        double num4 = Math.atan(Y / X);
        if (num2 >= 0) {
            if (num3 < 0) {
                num4 = 6.2831853071795862 + num4;
            }
        } else {
            num4 = 3.1415926535897931 + num4;
        }
        double num5 = Math.sqrt(X * X + Y * Y);
        double num6 = Math.atan(Z / num5);
        double num9;
        do {
            num++;
            double num7 = num6;
            double num8 = a / Math.sqrt(1.0 - e * e * Math.pow(Math.sin(num6), 2.0));
            num9 = num5 / Math.cos(num6) - num8;
            num6 = Math.atan((Z + num8 * e * e * Math.sin(num7)) / num5);
            num2 = (int) ((Math.abs(num6 - num7) - Err) * 1000000.0);
        }
        while (num2 > 0 || num < 20);

        double[] out = new double[3];
        out[1] = num4;
        out[0] = num6;
        out[2] = num9;
        return out;
    }

    public synchronized static double[] transformBL2XY(double b, double l) {
        double num = 0.0;
        double num2 = 0.0;
        double h = 0.0;
        double b2 = b * arc;
        double l2 = l * arc;
        double num3 = 0.0;
        double num4 = 0.0;
        double l3 = (edtD + edtM / 60.0 + edtS / 3600.0) * arc;
        double[] out1 = TransformUtil.BLH2XYZ(a84, e84, b2, l2, h);
        num = out1[0];
        num2 = out1[1];
        num3 = out1[2];
        double num5 = edtDX;
        double num6 = edtDY;
        double num7 = edtDZ;
        double num8 = edtRX;
        double num9 = edtRY;
        double num10 = edtRZ;
        double num11 = 1.0 + edtK;
        num8 /= 3600.0;
        num9 /= 3600.0;
        num10 /= 3600.0;
        num8 *= arc;
        num9 *= arc;
        num10 *= arc;
        num = num5 + num11 * num + num10 * num2 - num9 * num3;
        num2 = num6 + num11 * num2 - num10 * num + num8 * num3;
        num3 = num7 + num11 * num3 + num9 * num - num8 * num2;
        double[] out2 = TransformUtil.XYZ2BLH(a54, e54, num, num2, num3, 1E-08);
        b = out2[0];
        l = out2[1];
        num4 = out2[2];
        return TransformUtil.BL2XY(a54, e54, l3, b, l);
    }
}
