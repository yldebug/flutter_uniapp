#import "FlutterUniappPlugin.h"
#if __has_include(<flutter_uniapp/flutter_uniapp-Swift.h>)
#import <flutter_uniapp/flutter_uniapp-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_uniapp-Swift.h"
#endif

@implementation FlutterUniappPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterUniappPlugin registerWithRegistrar:registrar];
}
@end
