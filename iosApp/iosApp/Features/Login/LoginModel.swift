import Shared
import SwiftUI

class LoginModel: ViewModelWrapper<LoginViewModel> {
    
    @Published var state: LoginContractState = .init(name: nil, isValid: nil)
    
    @Published var showLoginError = false
    @Published var navigateToChat = false
    
    init() {
        
        super.init(viewModel: KoinViewModels().login)
        
        viewModel.collect(flow: viewModel.state, collect: { state in
            
            self.state = state as! LoginContractState
        })
        
        viewModel.collect(flow: viewModel.effect) { uiEffect in
            
            let effect = (uiEffect as! LoginContractEffect)
            
            switch effect {
            case is LoginContractEffectAuthError:
                self.showLoginError = true
            case is LoginContractEffectAuthSuccess:
                self.navigateToChat = true
            default:
                break
            }
        }
    }
    
    override func resetState() {
        
        showLoginError = false
        navigateToChat = false
    }
}
