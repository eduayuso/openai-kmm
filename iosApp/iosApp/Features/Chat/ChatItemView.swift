import SwiftUI
import Shared

struct ChatItemView: View {
    
    var message: MessageEntity
    
    var body: some View {
        
        VStack {
            
            TextCardView(message: message)
            HStack {
                if !message.fromOpenAi {
                    Spacer()
                }
                AuthorImage(message: message)
                AuthorText(message: message)
                if message.fromOpenAi {
                    Spacer()
                }
            }
            .padding(.all, 4)
        }
        .padding(.bottom, 24)
    }
}

struct TextCardView: View {
    
    let message: MessageEntity
    let lightBlue = Color(red: 0.8, green: 0.8, blue: 1.0)
    
    var body: some View {
        
        Text(message.text)
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(18)
            .background(message.fromOpenAi ? .white : lightBlue)
            .cornerRadius(15.0)
            .foregroundColor(message.fromOpenAi ? .gray : .black)
            .padding(.trailing, message.fromOpenAi ? 48 : 0)
            .padding(.leading, message.fromOpenAi ? 0 : 48)
    }
}

struct AuthorText: View {
    
    let message: MessageEntity
    
    var body: some View {
        
        Text(message.fromOpenAi ? "OpenAI" : "You")
    }
}

struct AuthorImage: View {
    
    let message: MessageEntity
    
    var body: some View {
        
        if message.fromOpenAi {
            Image(uiImage: UIImage(named: "OpenAIIcon")!)
                .resizable()
                .scaledToFit()
                .frame(width: 18, height: 18)
        } else {
            Image(systemName: "person.fill")
        }
    }
}

struct ChatItemViewPreviews: PreviewProvider {
    
    static var previews: some View {
        
        let message: MessageEntity = .init(text: "hola k ase",
                                           fromOpenAi: false,
                                           isLoading: false,
                                           isError: false)
        
        ChatItemView(message: message)
    }
}
