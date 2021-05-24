#import "FlutterUniappPlugin.h"
#import "FlutterUniappManager.h"
#import "FUResultHandler.h"


@implementation FlutterUniappPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_uniapp"
            binaryMessenger:[registrar messenger]];
  FlutterUniappPlugin* instance = [[FlutterUniappPlugin alloc] init];
  [instance setChannel:channel registrar:registrar];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (instancetype)init {
  self = [super init];
  if (self) {
    self.manager = [FlutterUniappManager new];
  }
  return self;
}

- (void)setChannel:(FlutterMethodChannel *)channel registrar:(NSObject <FlutterPluginRegistrar> *)registrar {
  self.manager.methodHandler = channel;
  self.manager.registrar = registrar;
}



- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    FUResultHandler *handler = [[FUResultHandler alloc] initWithResult:result];
    
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  }else if ([@"init" isEqualToString:call.method]) {
      [_manager initEngine:handler];
  }else if ([@"startApp" isEqualToString:call.method]) {
      NSString *id = call.arguments[@"appId"];
      NSString *path = call.arguments[@"redirectPath"];
      [_manager startApp:id redirectPath:path resultHandler:handler];
  }else {
    result(FlutterMethodNotImplemented);
  }
}

@end
