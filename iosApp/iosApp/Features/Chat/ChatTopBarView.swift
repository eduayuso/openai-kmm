import SwiftUI
import Shared

struct ChatTopBarView: ViewModifier {
    
    @State private var isMenuVisible: Bool = false
    var viewModel: ChatModel
    
    @ViewBuilder func icon(of systemName: String) -> some View {
        Image(systemName: systemName)
            .resizable()
            .scaledToFit()
            .frame(width: 22, height: 22)
    }
    
    func body(content: Content) -> some View {
        
        ZStack {
            
            VStack(spacing: 0) {
                
                HStack {
                    Text("Chat with OpenAI")
                        .padding(.leading, 18)
                    Spacer()
                    HStack(alignment: .center, spacing: 24) {
                        Button {
                            isMenuVisible.toggle()
                        } label: {
                            icon(of: "ellipsis")
                        }.foregroundColor(Theme.Colors.primary)
                    }
                    .padding(.trailing, 18)
                }
                .padding(.vertical, 4)
                .padding(.horizontal, 8)
                
                Divider()
                    .padding(.top, 8)
                
                content
            }
            
            if isMenuVisible {
                ChatTopMenuView(viewModel: viewModel)
                    .offset(y: 42)
            }
        }
        .navigationBarHidden(true)
    }
}
