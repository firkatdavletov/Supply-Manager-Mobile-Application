import SwiftUI

struct RemoteImageCircle: View {
    let urlString: String

    private var imageURL: URL? {
        URL(string: urlString)
    }

    var body: some View {
        if let imageURL = imageURL {
            AsyncImage(url: imageURL) { phase in
                switch phase {
                case .success(let image):
                    image
                        .resizable()
                        .scaledToFill()
                        .frame(width: 44, height: 44)
                        .clipShape(Circle())
                default:
                    placeholder
                }
            }
        } else {
            placeholder
        }
    }

    private var placeholder: some View {
        Circle()
            .fill(Color("IceBlue"))
            .frame(width: 44, height: 44)
    }
}

struct RemoteImage: View {
    let urlString: String
    let cornerRadius: CGFloat

    private var imageURL: URL? {
        URL(string: urlString)
    }

    var body: some View {
        if let imageURL = imageURL {
            AsyncImage(url: imageURL) { phase in
                switch phase {
                case .success(let image):
                    image
                        .resizable()
                        .scaledToFill()
                        .clipped()
                default:
                    placeholder
                }
            }
            .cornerRadius(cornerRadius)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            placeholder
                .cornerRadius(cornerRadius)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }

    private var placeholder: some View {
        Color("IceBlue")
    }
}

#Preview {
    RemoteImage(urlString: "", cornerRadius: 16)
        .frame(width: 100, height: 100)
}
