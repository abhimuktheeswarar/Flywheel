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
            url: "https://github.com/abhimuktheeswarar/Flywheel/releases/download/v1.3.0/Flywheel.xcframework.zip",
            checksum: "a69b3d80522823f543da54185518e4b04ac19afe42f418fe793716ccf7d46fdb"
        ),
    ]
)
