#import <Flutter/Flutter.h>


@class FlutterUniappManager;

@interface FlutterUniappPlugin : NSObject<FlutterPlugin>

@property(nonatomic, strong) FlutterUniappManager *manager;

@end
