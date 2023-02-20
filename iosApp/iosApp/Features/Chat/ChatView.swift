import SwiftUI
import Resolver
import Shared

struct ChatView: View {

    @EnvironmentObject var router: Router<Path>
    @InjectedObject var viewModel: ChatModel
    @State var typingMessage: String = ""
    
    var body: some View {
        
        let list = viewModel.state.messageList ?? []
        
        VStack {
            List(list, id: \.self) { message in
                ChatItemView(message: message)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .listRowInsets(EdgeInsets())
            }
            .navigationBarBackButtonHidden(true)
            .modifier(ChatTopBarView(viewModel: viewModel))
            
            HStack {
                TextField("Type your question ...", text: $typingMessage)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(minHeight: CGFloat(30))
                Button(
                    action: {
                        viewModel.set(event: ChatContractEventOnSendMessage(text: typingMessage))
                        typingMessage = ""
                    }
                ) {
                    Image(systemName: "paperplane")
                }
            }.frame(minHeight: CGFloat(50)).padding()
        }
    }
}

struct ChatView_Preview: PreviewProvider {
    
    static var previews: some View {
        
        ChatView()
    }
}
