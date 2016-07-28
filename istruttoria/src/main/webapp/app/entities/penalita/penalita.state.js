(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('penalita', {
//            parent: 'entity',
//            url: '/penalita',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.penalita.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/penalita/penalitas.html',
//                    controller: 'PenalitaController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('penalita');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
        // Paginazione
        .state('penalita', {
            parent: 'entity',
            url: '/penalita?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.penalita.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/penalita/penalitas.html',
                    controller: 'PenalitaController',
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
                    $translatePartialLoader.addPart('penalita');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        
        .state('penalita-detail', {
            parent: 'entity',
            url: '/penalita/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.penalita.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/penalita/penalita-detail.html',
                    controller: 'PenalitaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('penalita');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Penalita', function($stateParams, Penalita) {
                    return Penalita.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('penalita.new', {
            parent: 'penalita',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/penalita/penalita-dialog.html',
                    controller: 'PenalitaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                decoTipoPena: null,
                                qntaPena: null,
                                impoPena: null,
                                unitMisu: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('penalita', null, { reload: true });
                }, function() {
                    $state.go('penalita');
                });
            }]
        })
        .state('penalita.edit', {
            parent: 'penalita',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/penalita/penalita-dialog.html',
                    controller: 'PenalitaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Penalita', function(Penalita) {
                            return Penalita.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('penalita', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('penalita.delete', {
            parent: 'penalita',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/penalita/penalita-delete-dialog.html',
                    controller: 'PenalitaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Penalita', function(Penalita) {
                            return Penalita.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('penalita', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
