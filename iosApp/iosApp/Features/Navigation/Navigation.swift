import SwiftUI

final class Router<T: Hashable>: ObservableObject {
    
    @Published var paths: [T] = []
    
    func push(_ path: T) {
        paths.append(path)
    }
}

enum Path {
    
    case login
    case chat
    case settings
}
