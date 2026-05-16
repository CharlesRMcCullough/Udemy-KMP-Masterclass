//
//  AboutScreen.swift
//  iosApp
//
//  Created by Charles McCullough on 5/8/26.
//

import SwiftUI

struct AboutScreen: View {
    var body: some View {
      NavigationStack {
        AboutListView()
          .navigationTitle("About Device")
          
      }
    }
}

#Preview {
    AboutScreen()
}
