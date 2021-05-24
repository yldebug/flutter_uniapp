#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint flutter_uniapp.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'flutter_uniapp'
  s.version          = '0.0.1'
  s.summary          = 'A new Flutter plugin.'
  s.description      = <<-DESC
A new Flutter plugin.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.platform = :ios, '8.0'
  #  s.preserve_paths = ['UniMP/*.a','UniMP/*.framework']
   #  s.vendored_libraries = 'UniMP/*.a'
   #  s.vendored_frameworks = 'UniMP/*.framework'

  s.static_framework = true
  s.frameworks = [
      'JavaScriptCore','CoreMedia','MediaPlayer','AVFoundation','AVKit','GLKit','OpenGLES','CoreText','QuartzCore','CoreGraphics','QuickLook','CoreTelephony','AssetsLibrary','CoreLocation','AddressBook'
      ]
  s.libraries = ['iconv','c++']

    # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386','OTHER_LDFLAGS' => '-ObjC' }
end

