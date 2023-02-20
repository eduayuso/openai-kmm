import Foundation
import Resolver
import Shared

extension Resolver: ResolverRegistering {
    
    public static func registerAllServices() {

        register { LoginModel() }.asSingleton()
        register { ChatModel() }.asSingleton()
        register { SettingsModel() }.asSingleton()
    }
}

extension ResolverOptions {
    
    func asSingleton() {
        
        self.scope(.application)
    }
}
