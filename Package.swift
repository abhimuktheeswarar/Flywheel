// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "Flywheel",
    platforms: [.iOS("11"), .watchOS("4"), .tvOS("11"), .macOS("10.13"),],
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
