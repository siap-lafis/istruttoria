(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
// .state('elenco-pagamento', {
//            parent: 'entity',
//            url: '/elenco-pagamento',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.elencoPagamento.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/elenco-pagamento/elenco-pagamentos.html',
//                    controller: 'ElencoPagamentoController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('elencoPagamento');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })    
        // Paginazione
        .state('elenco-pagamento', {
            parent: 'entity',
          url: '/elenco-pagamento',
          data: {
              authorities: ['ROLE_USER'],
              pageTitle: 'istruttoriaApp.elencoPagamento.home.title'
          },
          views: {
              'content@': {
                  templateUrl: 'app/entities/elenco-pagamento/elenco-pagamentos.html',
                  controller: 'ElencoPagamentoController',
                  controllerAs: 'vm'
              }
          },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: {
                    value: null,
                    squash: true
                },
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('elencoPagamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('elenco-pagamento-detail', {
            parent: 'entity',
            url: '/elenco-pagamento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.elencoPagamento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/elenco-pagamento/elenco-pagamento-detail.html',
                    controller: 'ElencoPagamentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('elencoPagamento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ElencoPagamento', function($stateParams, ElencoPagamento) {
                    return ElencoPagamento.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('elenco-pagamento.new', {
            parent: 'elenco-pagamento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/elenco-pagamento/elenco-pagamento-dialog.html',
                    controller: 'ElencoPagamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idDecr: null,
                                dataDecr: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('elenco-pagamento', null, { reload: true });
                }, function() {
                    $state.go('elenco-pagamento');
                });
            }]
        })
        .state('elenco-pagamento.edit', {
            parent: 'elenco-pagamento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/elenco-pagamento/elenco-pagamento-dialog.html',
                    controller: 'ElencoPagamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ElencoPagamento', function(ElencoPagamento) {
                            return ElencoPagamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('elenco-pagamento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('elenco-pagamento.delete', {
            parent: 'elenco-pagamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/elenco-pagamento/elenco-pagamento-delete-dialog.html',
                    controller: 'ElencoPagamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ElencoPagamento', function(ElencoPagamento) {
                            return ElencoPagamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('elenco-pagamento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
