import SwiftUI

struct ContentView: View {
    
    @ObservedObject
    var router = Router<Path>()
    
    var body: some View {
        
        NavigationStack(path: $router.paths) {
            LoginView()
            .navigationDestination(for: Path.self) { path in
                switch path {
                    case .login: LoginView()
                    case .chat: ChatView()
                    case .settings: SettingsView()
                }
            }
        }.environmentObject(router)
    }
}
