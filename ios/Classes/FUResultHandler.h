//
//  FUResultHandler.h
//  flutter_uniapp
//
//  Created by luo on 2021/5/19.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>


@interface FUResultHandler : NSObject

@property(nonatomic, strong) FlutterResult result;

+ (instancetype)handlerWithResult:(FlutterResult)result;

- (instancetype)initWithResult:(FlutterResult)result;

- (void)replyError:(NSString *)errorCode;

- (void)reply:(id)obj;

- (void)notImplemented;

- (BOOL)isReplied;

- (void)replyEmpty;

@end
