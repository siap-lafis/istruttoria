(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('domanda', {
//            parent: 'entity',
//            url: '/domanda',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.domanda.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/domanda/domandas.html',
//                    controller: 'DomandaController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('domanda');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
        // Paginazione
        .state('domanda', {
            parent: 'entity',
            url: '/domanda?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                ppageTitle: 'istruttoriaApp.domanda.home.title'
            },
            views: {
            	'content@': {
            		templateUrl: 'app/entities/domanda/domandas.html',
            		controller: 'DomandaController',
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
                    $translatePartialLoader.addPart('domanda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        
        .state('domanda-detail', {
            parent: 'entity',
            url: '/domanda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.domanda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/domanda/domanda-detail.html',
                    controller: 'DomandaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('domanda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Domanda', function($stateParams, Domanda) {
                    return Domanda.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('domanda.new', {
            parent: 'domanda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domanda/domanda-dialog.html',
                    controller: 'DomandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idDomanda: null,
                                dataPres: null,
                                dataInse: null,
                                codSettore: null,
                                anno: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('domanda', null, { reload: true });
                }, function() {
                    $state.go('domanda');
                });
            }]
        })
        .state('domanda.edit', {
            parent: 'domanda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domanda/domanda-dialog.html',
                    controller: 'DomandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Domanda', function(Domanda) {
                            return Domanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('domanda', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('domanda.delete', {
            parent: 'domanda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domanda/domanda-delete-dialog.html',
                    controller: 'DomandaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Domanda', function(Domanda) {
                            return Domanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('domanda', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('domanda.soggetto', {
        	parent: 'entity',
            url: '/domanda/soggetto/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                ppageTitle: 'istruttoriaApp.domanda.home.title'
            },
            views: {
            	'content@': {
            		templateUrl: 'app/entities/domanda/domandas.html',
            		controller: 'DomandeSoggettoController',
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
                    $translatePartialLoader.addPart('domanda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }       
        });      
    }

})();
