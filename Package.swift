// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "Flywheel",
    platforms: [.iOS("16.0"), .watchOS("7.0"), .tvOS("16.0"), .macOS("13.0")],
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
