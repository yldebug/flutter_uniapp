//
//  FUResultHandler.m
//  flutter_uniapp
//
//  Created by luo on 2021/5/19.
//

#import "FUResultHandler.h"


@implementation FUResultHandler {

  BOOL isReply;
}
- (instancetype)initWithResult:(FlutterResult)result {
  self = [super init];
  if (self) {
    self.result = result;
    isReply = NO;
  }

  return self;
}

+ (instancetype)handlerWithResult:(FlutterResult)result {
  return [[self alloc] initWithResult:result];
}

- (void)reply:(id)obj {
  if (isReply) {
    return;
  }
  isReply = YES;

  dispatch_async(dispatch_get_main_queue(), ^{
      self.result(obj);
  });
}

- (void)replyError:(NSString *)errorCode {
  if (isReply) {
    return;
  }
  isReply = YES;
  dispatch_async(dispatch_get_main_queue(), ^{
      FlutterError *error = [FlutterError errorWithCode:errorCode message:nil details:nil];
      self.result(error);
  });

}

- (void)notImplemented {
  if (isReply) {
    return;
  }
  isReply = YES;
  dispatch_async(dispatch_get_main_queue(), ^{
      self.result(FlutterMethodNotImplemented);
  });

}

- (BOOL)isReplied {
  return isReply;
}

- (void)replyEmpty {
  [self reply:@1];
}

@end
