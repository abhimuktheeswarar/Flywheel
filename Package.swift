// swift-tools-version:5.9
import PackageDescription

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
            url: "https://github.com/abhimuktheeswarar/Flywheel/releases/download/v1.2.0/Flywheel.xcframework.zip",
            checksum: "f02117bfecdc3fe3a749e523de17fa20a81fe23ce4a0a18bedb8f0227ce0d23d"
        ),
    ]
)
