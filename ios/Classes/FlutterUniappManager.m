//
//  FlutterUniappManager.m
//  flutter_uniapp
//
//  Created by luo on 2021/5/19.
//

#import "FlutterUniappManager.h"
#import "FUResultHandler.h"
#import "DCUniMP.h"


@interface FlutterUniappManager () <DCUniMPSDKEngineDelegate>
@property (nonatomic, weak) DCUniMPInstance *uniMPInstance; /**< 保存当前打开的小程序应用的引用 注意：请使用 weak 修辞，否则应在关闭小程序时置为 nil */

@end

@implementation FlutterUniappManager {
    
}

// 初始化引擎
- (void)initEngine:(FUResultHandler *)resultHandler {
    NSDictionary *options = [NSDictionary dictionaryWithObject:[NSNumber numberWithBool:YES] forKey:@"debug"];
    [DCUniMPSDKEngine initSDKEnvironmentWithLaunchOptions:options];
    [resultHandler reply:@{@"result": @YES}];
}
// 预加载小程序后启动
- (void)startApp:(NSString *)id redirectPath:(NSString *) path resultHandler:(FUResultHandler *)resultHandler{
    
    //释放小程序资源
    BOOL *isResource =[self checkUniMPResource:id];
    
    if (isResource){
        // 获取配置信息
        DCUniMPConfiguration *configuration = [self getUniMPConfiguration];
        __weak __typeof(self)weakSelf = self;
        // 预加载小程序
        [DCUniMPSDKEngine preloadUniMP:id configuration:configuration completed:^(DCUniMPInstance * _Nullable uniMPInstance, NSError * _Nullable error) {
            if (uniMPInstance) {
                weakSelf.uniMPInstance = uniMPInstance;
                // 预加载后打开小程序
                [uniMPInstance showWithCompletion:^(BOOL success, NSError * _Nullable error) {
                    if (error) {
                        NSLog(@"show 小程序失败：%@",error);
                        [resultHandler reply:@{@"result": @NO, @"error": [error localizedDescription]}];
                        return;
                    }
                }];
            } else {
                NSLog(@"预加载小程序出错：%@",error);
                [resultHandler reply:@{@"result": @NO, @"error": [error localizedDescription]}];
                return;
            }
        }];
        [resultHandler reply:@{@"result": @YES}];
        return;
    }
    [resultHandler reply:@{@"result": @NO, @"error": @"获取小程序资源失败"}];
}


- (BOOL)checkUniMPResource:(NSString *)id {
#warning 注意：isExistsApp: 方法判断的仅是运行路径中是否有对应的应用资源，宿主还需要做好内置wgt版本的管理，如果更新了内置的wgt也应该执行 releaseAppResourceToRunPathWithAppid 方法应用最新的资源
    if (![DCUniMPSDKEngine isExistsApp:id]) {
        // 读取导入到工程中的wgt应用资源
        NSString *appResourcePath = [[NSBundle mainBundle] pathForResource:id ofType:@"wgt"];
        if (!appResourcePath) {
            NSLog(@"资源路径不正确，请检查");
            return false;
        }
        // 将应用资源部署到运行路径中
        if ([DCUniMPSDKEngine releaseAppResourceToRunPathWithAppid:id resourceFilePath:appResourcePath]) {
            NSLog(@"应用资源文件部署成功");
        }
    }
    return true;
}

/// 小程序配置信息
- (DCUniMPConfiguration *)getUniMPConfiguration {
    /// 初始化小程序的配置信息
    DCUniMPConfiguration *configuration = [[DCUniMPConfiguration alloc] init];
    
    // 配置启动小程序时传递的参数（参数可以在小程序中通过 plus.runtime.arguments 获取此参数）
//    configuration.arguments = @{ @"arguments":@"Hello uni microprogram" };
    // 配置小程序启动后直接打开的页面路径 例：@"pages/component/view/view?action=redirect&password=123456"
//    configuration.redirectPath = @"pages/component/view/view?action=redirect&password=123456";
    // 开启后台运行
    configuration.enableBackground = NO;
    // 设置 push 打开方式
    configuration.openMode = DCUniMPOpenModePush;
    // 启用侧滑手势关闭小程序
    configuration.enableGestureClose = NO;
    
    
    return configuration;
}
@end

