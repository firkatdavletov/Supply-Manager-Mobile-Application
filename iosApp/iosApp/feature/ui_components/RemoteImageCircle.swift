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
    let height: CGFloat
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
                        .frame(height: height)
                        .clipped()
                default:
                    placeholder
                }
            }
            .cornerRadius(cornerRadius)
        } else {
            placeholder
                .cornerRadius(cornerRadius)
        }
    }

    private var placeholder: some View {
        Color("IceBlue")
            .frame(height: height)
    }
}
