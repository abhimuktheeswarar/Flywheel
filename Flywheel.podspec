Pod::Spec.new do |spec|
    spec.name                     = 'Flywheel'
    spec.version                  = '1.1.6'
    spec.homepage                 = 'https://github.com/abhimuktheeswarar/Flywheel'
    spec.source                   = { :git => "https://github.com/abhimuktheeswarar/Flywheel.git", :tag => "v#{spec.version}" }
    spec.authors                  = 'Abhi Muktheeswarar'
    spec.license                  = 'The Apache Software License, Version 2.0'
    spec.summary                  = 'Kotlin-Multiplatform state management library.'
    spec.static_framework         = true
    spec.vendored_frameworks      = "flywheel/xcframework/Flywheel.xcframework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"
    spec.platforms                = { :ios => '16.0', :watchos => '9.0', :tvos => '16.0', :osx => '13.0' }
    spec.pod_target_xcconfig      = { 'ONLY_ACTIVE_ARCH' => 'YES' }
end
