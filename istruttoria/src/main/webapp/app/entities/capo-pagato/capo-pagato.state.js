(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('capo-pagato', {
//            parent: 'entity',
//            url: '/capo-pagato',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.capoPagato.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/capo-pagato/capo-pagatoes.html',
//                    controller: 'CapoPagatoController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('capoPagato');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
//        
        // Paginazione
        .state('capo-pagato', {
            parent: 'entity',
            url: '/capo-pagato?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.capoPagato.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/capo-pagato/capo-pagatoes.html',
                    controller: 'CapoPagatoController',
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
                    $translatePartialLoader.addPart('capoPagato');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('capo-pagato-detail', {
            parent: 'entity',
            url: '/capo-pagato/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.capoPagato.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/capo-pagato/capo-pagato-detail.html',
                    controller: 'CapoPagatoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('capoPagato');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CapoPagato', function($stateParams, CapoPagato) {
                    return CapoPagato.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('capo-pagato.new', {
            parent: 'capo-pagato',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capo-pagato/capo-pagato-dialog.html',
                    controller: 'CapoPagatoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                marcaCapo: null,
                                ammissibile: null,
                                numUba: null,
                                mancanzaAnalisiLatte: null,
                                medieLatteSoma: null,
                                medieLatteGerm: null,
                                medieLatteProt: null,
                                codAsl: null,
                                flagSess: null,
                                dataNasc: null,
                                codiRazz: null,
                                dataInizDete: null,
                                dataFineDete: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('capo-pagato', null, { reload: true });
                }, function() {
                    $state.go('capo-pagato');
                });
            }]
        })
        .state('capo-pagato.edit', {
            parent: 'capo-pagato',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capo-pagato/capo-pagato-dialog.html',
                    controller: 'CapoPagatoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CapoPagato', function(CapoPagato) {
                            return CapoPagato.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('capo-pagato', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('capo-pagato.delete', {
            parent: 'capo-pagato',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capo-pagato/capo-pagato-delete-dialog.html',
                    controller: 'CapoPagatoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CapoPagato', function(CapoPagato) {
                            return CapoPagato.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('capo-pagato', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('capo-pagato.pagamento', {
            parent: 'entity',
            url: '/capo-pagato/pagamento/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.capoPagato.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/capo-pagato/capo-pagatoes.html',
                    controller: 'CapiPagatiPagamentoController',
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
                    $translatePartialLoader.addPart('capoPagato');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        ;
    }

})();
