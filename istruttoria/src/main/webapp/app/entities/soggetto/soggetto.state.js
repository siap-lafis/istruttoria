(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('soggetto', {
//            parent: 'entity',
//            url: '/soggetto',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.soggetto.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/soggetto/soggettos.html',
//                    controller: 'SoggettoController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('soggetto');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
        
        // Paginazione
        .state('soggetto', {
            parent: 'entity',
            url: '/soggetto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.soggetto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/soggetto/soggettos.html',
                    controller: 'SoggettoController',
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
                    $translatePartialLoader.addPart('soggetto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        
        .state('soggetto-detail', {
            parent: 'entity',
            url: '/soggetto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.soggetto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/soggetto/soggetto-detail.html',
                    controller: 'SoggettoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('soggetto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Soggetto', function($stateParams, Soggetto) {
                    return Soggetto.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('soggetto.new', {
            parent: 'soggetto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soggetto/soggetto-dialog.html',
                    controller: 'SoggettoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cuaa: null,
                                denominazione: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('soggetto', null, { reload: true });
                }, function() {
                    $state.go('soggetto');
                });
            }]
        })
        .state('soggetto.edit', {
            parent: 'soggetto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soggetto/soggetto-dialog.html',
                    controller: 'SoggettoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Soggetto', function(Soggetto) {
                            return Soggetto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('soggetto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('soggetto.delete', {
            parent: 'soggetto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soggetto/soggetto-delete-dialog.html',
                    controller: 'SoggettoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Soggetto', function(Soggetto) {
                            return Soggetto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('soggetto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
