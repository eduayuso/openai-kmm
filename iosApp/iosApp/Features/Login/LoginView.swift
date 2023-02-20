import SwiftUI
import Resolver
import Shared

let lightGreyColor = Color(red: 239.0/255.0, green: 243.0/255.0, blue: 244.0/255.0, opacity: 1.0)

struct LoginView: View {
    
    @EnvironmentObject var router: Router<Path>
    @InjectedObject var viewModel: LoginModel
    @State var username: String = ""
    
    var body: some View {
        
        let usernameBinding = Binding<String>(get: {
            username
        }, set: {
            username = $0
            let event = LoginContractEventValidateForm(user: username)
            viewModel.set(event: event)
        })
        
        VStack {
            LoginText()
            UserTextField(binding: usernameBinding, viewModel: viewModel)
            LoginButton {
                let authEvent = LoginContractEventAuthenticate(user: username)
                viewModel.set(event: authEvent)
            }
            Spacer()
        }
        .padding()
        .alert("Please, enter a name", isPresented: $viewModel.showLoginError) {}
        .onChange(of: viewModel.navigateToChat) { value in
            if value {
                viewModel.resetState()
                router.push(.chat)
            }
        }
    }
}

struct LoginText: View {
    
    var body: some View {
        
        return Text("Welcome!")
            .font(.largeTitle)
            .fontWeight(.semibold)
            .padding(.bottom, 20)
    }
}

struct UserTextField: View {
    
    @Binding var binding: String
    let viewModel: LoginModel
    
    var body: some View {
        
        return HStack {
            TextField("Enter your name", text: $binding)
                .autocapitalization(.none)
            if viewModel.state.isValid == true {
                Image(systemName: "checkmark")
                    .foregroundColor(Color.green)
            } else if viewModel.state.isValid == false {
                Image(systemName: "exclamationmark.circle.fill")
                    .foregroundColor(Color.red)
            }
        }
        .padding()
        .background(lightGreyColor)
    }
}

struct LoginButton: View {
    
    private let loginAction : () -> Void
    
    init(action: @escaping () -> Void) {
        loginAction = action
    }
    
    var body: some View {
        
        return Button(
            action: { loginAction() }
        ) {
            Text("Enter")
                .font(.headline)
                .foregroundColor(.white)
                .frame(width: 220, height: 60)
                .background(.blue)
                .cornerRadius(15.0)
                .padding(18)
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    
    static var previews: some View {
        
        LoginView()
    }
}
