#-keep public class * extends cn.dozyx.lib.MySuperClass
# 不混淆 MySuperClass 子类的类名，但它的成员会被混淆，没有使用的方法也会移除。MySuperClass 也会被混淆。
# 没有被使用的类也不会被移除

-keep public class * extends cn.dozyx.lib.MySuperClass {
  public protected *;
  #  不混淆 public/protected 的成员
}

#-dontwarn cn.dozyx.lib.**
#-keep class * extends cn.dozyx.lib2.CompileOnlySuperClass
#-keepclassmembers public class ** extends cn.dozyx.lib2.CompileOnlySuperClass
# 对于 compileOnly 类的子类，这条规则并不能避免子类被混淆
#-keep class cn.dozyx.lib.MySubComplileOnlyClass
#-keep public class cn.dozyx.lib2.CompileOnlySuperClass {
#  public protected *;
#}

#-keep class cn.dozyx.lib2.**{
#  public protected *;
#}