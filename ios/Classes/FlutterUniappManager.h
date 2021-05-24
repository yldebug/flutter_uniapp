//
//  FlutterUniappManager.h
//  flutter_uniapp
//
//  Created by luo on 2021/5/19.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>

@class FUResultHandler;

@interface FlutterUniappManager : NSObject

@property(nonatomic, strong) FlutterMethodChannel *methodHandler;

@property(nonatomic, strong) NSObject <FlutterPluginRegistrar> *registrar;



- (void)initEngine:(FUResultHandler *)resultHandler;

- (void)startApp:(NSString *)id redirectPath:(NSString *) path resultHandler:(FUResultHandler *)resultHandler;


@end
