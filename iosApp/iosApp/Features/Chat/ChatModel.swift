import SwiftUI
import Shared

class ChatModel: ViewModelWrapper<ChatViewModel> {
    
    @Published var state: ChatContractState = .init(settings: nil,
                                                    messageList: nil,
                                                    isError: false)
    
    @Published var navigateToLogin = false
    @Published var navigateToSettings = false
    
    init() {
        
        super.init(viewModel: KoinViewModels().chat)
        
        viewModel.collect(flow: viewModel.state, collect: { state in
            
            self.state = state as! ChatContractState
        })
        
        viewModel.collect(flow: viewModel.effect) { uiEffect in
            
            let effect = (uiEffect as! ChatContractEffect)
            
            switch effect {
            case is ChatContractEffectNavigateToLogin:
                self.navigateToLogin = true
            case is ChatContractEffectNavigateToSettings:
                self.navigateToSettings = true
            default:
                break
            }
        }
        
        let initEvent = ChatContractEventOnGetSettings()
        set(event: initEvent)
    }
    
    override func resetState() {
        
    }
}
