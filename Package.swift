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
            url: "https://github.com/abhimuktheeswarar/Flywheel/releases/download/v1.1.6/Flywheel.xcframework.zip",
            checksum: "866703fd1845edc4a83a2d346d8291d5fbc0f8090533700f726c64eb35e9606e"
        ),
    ]
)
