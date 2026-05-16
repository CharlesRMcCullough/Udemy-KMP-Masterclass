//
//  ArticlesScreen.swift
//  iosApp
//
//  Created by Charles McCullough on 5/16/26.
//

import Shared
import SwiftUI

extension ArticlesScreen {
    
    @MainActor
    class ArticlesViewModelWrapper: ObservableObject {
        private let articlesViewModel: ArticlesViewModel
        private var observationHandle: CancellableHandle?

        @Published var articlesState: ArticlesState
        
        init() {
            articlesViewModel = ArticlesViewModel()
            articlesState = articlesViewModel.articlesState.value as! ArticlesState
        }
        
        func startObserving() {
            guard observationHandle == nil else {
                return
            }

            observationHandle = articlesViewModel.observeArticlesState { [weak self] state in
                Task { @MainActor in
                    self?.articlesState = state
                }
            }
        }

        func stopObserving() {
            observationHandle?.cancel()
            observationHandle = nil
            articlesViewModel.clear()
        }

        func loadArticles() {
            articlesViewModel.loadArticles()
        }

        deinit {
            observationHandle?.cancel()
            articlesViewModel.clear()
        }
    }
}

struct ArticlesScreen: View {

    @StateObject private var viewModel = ArticlesViewModelWrapper()
    @State private var shouldOpenAbout = false

    var body: some View {
        NavigationStack {
            VStack {
                if viewModel.articlesState.loading {
                    Loader()
                }

                if let error = viewModel.articlesState.error {
                    ErrorMessage(message: error)
                }

                if !viewModel.articlesState.articles.isEmpty {
                    ScrollView {
                        LazyVStack(spacing: 10) {
                            ForEach(viewModel.articlesState.articles, id: \.title) { article in
                                ArticleItemView(article: article)
                            }
                        }
                    }
                    .refreshable {
                        viewModel.loadArticles()
                    }
                }

            }
            .navigationTitle("Articles")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button {
                        shouldOpenAbout = true
                    } label: {
                        Image(systemName: "info.circle")
                    }
                }
            }
            .sheet(isPresented: $shouldOpenAbout) {
                AboutScreen()
            }
            .onAppear {
                viewModel.startObserving()
            }
            .onDisappear {
                viewModel.stopObserving()
            }
        }
    }
}

struct ArticleItemView: View {
    var article: Article
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            AsyncImage(url: URL(string: article.imageUrl)) { phase in
                if phase.image != nil {
                    phase.image!
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                } else if phase.error != nil {
                    Text("Image Load Error")
                } else {
                    ProgressView()
                }
            }
            Text(article.title)
                .font(.title)
                .fontWeight(.bold)
            Text(article.desc)
            Text(article.date)
                .frame(maxWidth: .infinity, alignment: .trailing)
                .foregroundStyle(.gray)
        }
        .padding(16)
    }
}

struct Loader: View {
    var body: some View {
        ProgressView()
    }
}

struct ErrorMessage: View {
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}
