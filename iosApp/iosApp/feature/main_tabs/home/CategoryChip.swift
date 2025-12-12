//
//  CategoryChip.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 17/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct CategoryChip : View {
    let title: String
    let imageUrl: String?
    
    var body: some View {
        HStack(alignment: .center, spacing: 12) {
            RemoteImageCircle(
                urlString: imageUrl != nil ? imageUrl! : "",
            )
            
            Text(title)
                .font(.system(size: 14, weight: .bold, design: .rounded))
                .foregroundColor(Color("BlackColor"))
        }
        .padding(8)
        .frame(height: 60)
        .background(Color(.white))
        .clipShape(RoundedCorner(radius: 30))
        .shadow(color: Color("LightGrey"), radius: 4)
    }
}
