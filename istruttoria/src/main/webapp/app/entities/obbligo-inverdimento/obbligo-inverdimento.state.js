(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
//        .state('obbligo-inverdimento', {
//            parent: 'entity',
//            url: '/obbligo-inverdimento',
//            data: {
//                authorities: ['ROLE_USER'],
//                pageTitle: 'istruttoriaApp.obbligoInverdimento.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimentos.html',
//                    controller: 'ObbligoInverdimentoController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('obbligoInverdimento');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        })
        
        // Paginazione
        .state('obbligo-inverdimento', {
            parent: 'entity',
            url: '/obbligo-inverdimento?page&sort&search',
            data: {
            	authorities: ['ROLE_USER'],
            	pageTitle: 'istruttoriaApp.obbligoInverdimento.home.title'
            },
            views: {
            	'content@': {
            		templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimentos.html',
            		controller: 'ObbligoInverdimentoController',
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
                       $translatePartialLoader.addPart('obbligoInverdimento');
                       $translatePartialLoader.addPart('global');
                       return $translate.refresh();
                   }]
            }
        })
        
        .state('obbligo-inverdimento-detail', {
            parent: 'entity',
            url: '/obbligo-inverdimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.obbligoInverdimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimento-detail.html',
                    controller: 'ObbligoInverdimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('obbligoInverdimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ObbligoInverdimento', function($stateParams, ObbligoInverdimento) {
                    return ObbligoInverdimento.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('obbligo-inverdimento.new', {
            parent: 'obbligo-inverdimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimento-dialog.html',
                    controller: 'ObbligoInverdimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                supeSemi: null,
                                supePratPerm: null,
                                supeFora: null,
                                decoEsonDive: null,
                                decoEsonEfa: null,
                                flagRispColt: null,
                                flagRispColtRima: null,
                                flagRisp75P: null,
                                flagRisp95P: null,
                                flagRispEfa: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('obbligo-inverdimento', null, { reload: true });
                }, function() {
                    $state.go('obbligo-inverdimento');
                });
            }]
        })
        .state('obbligo-inverdimento.edit', {
            parent: 'obbligo-inverdimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimento-dialog.html',
                    controller: 'ObbligoInverdimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ObbligoInverdimento', function(ObbligoInverdimento) {
                            return ObbligoInverdimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('obbligo-inverdimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('obbligo-inverdimento.delete', {
            parent: 'obbligo-inverdimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimento-delete-dialog.html',
                    controller: 'ObbligoInverdimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ObbligoInverdimento', function(ObbligoInverdimento) {
                            return ObbligoInverdimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('obbligo-inverdimento', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('obbligo-inverdimento.superficie-inverdimento', {
            parent: 'entity',
            url: '/obbligo-inverdimento/superficie-inverdimento/{id}?page&sort&search',
            data: {
            	authorities: ['ROLE_USER'],
            	pageTitle: 'istruttoriaApp.obbligoInverdimento.home.title'
            },
            views: {
            	'content@': {
            		templateUrl: 'app/entities/obbligo-inverdimento/obbligo-inverdimentos.html',
            		controller: 'ObblighiInverdimentoSuperficieInverdimentoController',
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
                       $translatePartialLoader.addPart('obbligoInverdimento');
                       $translatePartialLoader.addPart('global');
                       return $translate.refresh();
                   }]
            }
        })
        ;
    }

})();
