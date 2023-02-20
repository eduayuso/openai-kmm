import SwiftUI
import Shared

class SettingsModel: ViewModelWrapper<SettingsViewModel> {
    
    @Published var state: SettingsContractState = .init(settings: .init(user: nil, model: OpenAIModels.Babbage().id),
                                                        isUserValid: true,
                                                        isError: false)
    
    @Published var settingsSaved = false
    
    init() {
        
        super.init(viewModel: KoinViewModels().settings)
        
        viewModel.collect(flow: viewModel.state, collect: { state in
            
            self.state = state as! SettingsContractState
        })
        
        viewModel.collect(flow: viewModel.effect) { uiEffect in
            
            let effect = (uiEffect as! SettingsContractEffect)
            
            switch effect {
            case is SettingsContractEffectSettingsSaved:
                self.settingsSaved = true
            default:
                break
            }
        }
    }
    
    override func resetState() {
        
    }
}
