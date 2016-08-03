(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('pagamento', {
//            parent: 'entity',
//            url: '/pagamento',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.pagamento.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/pagamento/pagamentos.html',
//                    controller: 'PagamentoController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('pagamento');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
        // Paginazione
        .state('pagamento', {
            parent: 'entity',
            url: '/pagamento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.pagamento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pagamento/pagamentos.html',
                    controller: 'PagamentoController',
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
                    $translatePartialLoader.addPart('pagamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        
        .state('pagamento-detail', {
            parent: 'entity',
            url: '/pagamento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.pagamento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pagamento/pagamento-detail.html',
                    controller: 'PagamentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pagamento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pagamento', function($stateParams, Pagamento) {
                    return Pagamento.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('pagamento.new', {
            parent: 'pagamento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagamento/pagamento-dialog.html',
                    controller: 'PagamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codIntervento: null,
                                qntaDich: null,
                                qntaAmme: null,
                                qntaLiqu: null,
                                impoDich: null,
                                impoAmme: null,
                                impoLiqu: null,
                                statLiqu: null,
                                unitMisu: null,
                                codiNumeCapiSpes: null,
                                dataElab: null,
                                codiEsiGcol: null,
                                percSanzGcol: null,
                                percSanzAzie: null,
                                valoMediTito: null,
                                impoTratModu: null,
                                fascModu: null,
                                impoTratFina: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pagamento', null, { reload: true });
                }, function() {
                    $state.go('pagamento');
                });
            }]
        })
        .state('pagamento.edit', {
            parent: 'pagamento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagamento/pagamento-dialog.html',
                    controller: 'PagamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pagamento', function(Pagamento) {
                            return Pagamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pagamento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pagamento.delete', {
            parent: 'pagamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagamento/pagamento-delete-dialog.html',
                    controller: 'PagamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pagamento', function(Pagamento) {
                            return Pagamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pagamento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pagamento.domanda', {
            parent: 'entity',
            url: '/pagamento/domanda/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.pagamento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pagamento/pagamentos.html',
                    controller: 'PagamentiDomandaController',
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
                    $translatePartialLoader.addPart('superficie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });      
    }

})();
