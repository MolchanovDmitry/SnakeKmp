-dontobfuscate
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable,!code/simplification/cast
-allowaccessmodification
-repackageclasses ''

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
	public static void checkNotNull(...);
	public static void checkExpressionValueIsNotNull(...);
	public static void checkNotNullExpressionValue(...);
	public static void checkParameterIsNotNull(...);
	public static void checkNotNullParameter(...);
	public static void checkReturnedValueIsNotNull(...);
	public static void checkFieldIsNotNull(...);
	public static void throwUninitializedPropertyAccessException(...);
	public static void throwNpe(...);
	public static void throwJavaNpe(...);
	public static void throwAssert(...);
	public static void throwIllegalArgument(...);
	public static void throwIllegalState(...);
}
