Pod::Spec.new do |spec|
    spec.name                     = 'flywheel'
    spec.version                  = '1.0.0-RC'
    spec.homepage                 = 'https://github.com/abhimuktheeswarar/Flywheel'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = 'Abhi Muktheeswarar'
    spec.license                  = 'The Apache Software License, Version 2.0'
    spec.summary                  = 'Kotlin-Multiplatform state management library'

    spec.static_framework         = true
    spec.vendored_frameworks      = "build/cocoapods/framework/flywheel.framework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"

                

                

    spec.pod_target_xcconfig = {
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'ios_x64',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'ios_arm',
        'KOTLIN_TARGET[sdk=watchsimulator*]' => 'watchos_x64',
        'KOTLIN_TARGET[sdk=watchos*]' => 'watchos_arm',
        'KOTLIN_TARGET[sdk=appletvsimulator*]' => 'tvos_x64',
        'KOTLIN_TARGET[sdk=appletvos*]' => 'tvos_arm64',
        'KOTLIN_TARGET[sdk=macosx*]' => 'macos_x64'
    }

    spec.script_phases = [
        {
            :name => 'Build flywheel',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../../../../../private/var/folders/mp/g1wk29253ld97p6n0cgnwt200000gn/T/wraploc/gradlew" -p "$REPO_ROOT" :flywheel:syncFramework \
                    -Pkotlin.native.cocoapods.target=$KOTLIN_TARGET \
                    -Pkotlin.native.cocoapods.configuration=$CONFIGURATION \
                    -Pkotlin.native.cocoapods.cflags="$OTHER_CFLAGS" \
                    -Pkotlin.native.cocoapods.paths.headers="$HEADER_SEARCH_PATHS" \
                    -Pkotlin.native.cocoapods.paths.frameworks="$FRAMEWORK_SEARCH_PATHS"
            SCRIPT
        }
    ]
end