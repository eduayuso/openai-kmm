import SwiftUI
import Shared

struct ChatTopMenuView: View {
    
    @EnvironmentObject var router: Router<Path>
    @ObservedObject var viewModel: ChatModel
    
    private func userName() -> String {
        
        return "\(viewModel.state.settings?.user ?? "")"
    }
    
    var header: some View {
        
        VStack(spacing: 4) {
            VStack(alignment: .leading) {
                HStack(alignment: .bottom) {
                    userImage
                    Text(userName())
                        .font(.system(size: 20))
                }
            }
        }
        .padding(
            .init(
                top: 8,
                leading: 8,
                bottom: 8,
                trailing: 8)
        )
    }
    
    var userImage: some View {
        
        Image(systemName: "person.fill")
            .resizable()
            .frame(width: 18, height: 18, alignment: .center)
            .clipShape(Circle())
    }
    
    var body: some View {
        
        ZStack(alignment: .topTrailing) {
            Color.clear
            VStack(alignment: .leading) {
                header
                ChatMenuItem(
                    title: "Profile",
                    icon: "gearshape.fill"
                )
                .onTapGesture {
                    viewModel.set(event: ChatContractEventOnSettingsTapped())
                }
                ChatMenuItem(
                    title: "Info",
                    icon: "info.circle.fill"
                )
                .onTapGesture {
                }
                ChatMenuItem(
                    title: "Logout",
                    icon: "rectangle.portrait.and.arrow.right"
                )
                .onTapGesture {
                    viewModel.set(event: ChatContractEventOnLogout())
                }
                Divider()
            }
            .frame(minWidth: 0, maxWidth: 278, alignment: .topLeading)
            .background(Theme.Colors.menuBackground)
            .clipped()
            .shadow(color: Theme.Colors.shadow, radius: 2, x: 0, y: 2)
            Spacer()
        }
        .onChange(of: viewModel.navigateToLogin) { value in
            if value {
                viewModel.resetState()
                router.paths.popLast()
            }
        }
        .onChange(of: viewModel.navigateToSettings) { value in
            if value {
                router.push(.settings)
            }
        }
    }
}

struct ChatMenuItem: View {
    
    let title: String
    let icon: String
    
    var body: some View {
        
        Divider()
        HStack {
            Image(
                systemName: icon
            )
            Text(
                title
            )
            .font(.system(size: 14, weight: .light, design: .default))
        }
        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
        .padding(
            .init(
                top: 4,
                leading: 4,
                bottom: 4,
                trailing: 4)
        )
    }
}

struct ChatTopMenu_Previews: PreviewProvider {
    
    static var previews: some View {
        
        ChatTopMenuView(viewModel: ChatModel())
    }
}
