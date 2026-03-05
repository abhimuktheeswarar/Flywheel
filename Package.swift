// swift-tools-version:5.9
import PackageDescription

// Local-development Package.swift.
//
// The XCFramework referenced below is NOT checked into git.
// To populate it locally, run:
//   ./gradlew :flywheel:copyXCFrameworkToRepo
//
// For release/remote SPM consumption (URL + checksum), run:
//   ./gradlew :flywheel:generatePackageSwift
// and use the generated file at flywheel/build/spm/Package.swift

let package = Package(
    name: "Flywheel",
    platforms: [.iOS(.v16), .watchOS(.v9), .tvOS(.v16), .macOS(.v13)],
    products: [
        .library(
            name: "Flywheel",
            targets: ["Flywheel"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "Flywheel",
            path: "flywheel/xcframework/Flywheel.xcframework"
        ),
    ]
)
