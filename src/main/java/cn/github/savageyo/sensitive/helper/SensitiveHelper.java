package cn.github.savageyo.sensitive.helper;

/**
 * @Description 脱敏helper, 开启后，sql查询时将不处理脱敏流程
 * @Author Savage
 * @Date 2021/9/23
 */
public class SensitiveHelper {

  public static final ThreadLocal<Boolean> LOCAL_SENSITIVE = new ThreadLocal<>();

  public static void showSensitive() {
    LOCAL_SENSITIVE.set(true);
  }

  public static Boolean getSensitiveFLag() {
    return LOCAL_SENSITIVE.get();
  }

  public static void clean() {
    LOCAL_SENSITIVE.remove();
  }
}
