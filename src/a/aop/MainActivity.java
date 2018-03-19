package a.aop;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.loadLibrary("javahooker");
		System.loadLibrary("sbxa");
		for (final Member method : Activity.class.getDeclaredMethods()) {
			if (!Modifier.isPublic(method.getModifiers())) continue;
			if (method.getName().startsWith("on")) continue;
			
			XposedBridge.hookMethod(method, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(final MethodHookParam param) {
					XposedBridge.log(param.method.getName());
				}
			});
		}
	}
}
