(function() {
    'use strict';

    angular
        .module('istruttoriaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('superficie-pagata', {
            parent: 'entity',
            url: '/superficie-pagata',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.superficiePagata.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie-pagata/superficie-pagatas.html',
                    controller: 'SuperficiePagataController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('superficiePagata');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('superficie-pagata-detail', {
            parent: 'entity',
            url: '/superficie-pagata/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istruttoriaApp.superficiePagata.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/superficie-pagata/superficie-pagata-detail.html',
                    controller: 'SuperficiePagataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('superficiePagata');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SuperficiePagata', function($stateParams, SuperficiePagata) {
                    return SuperficiePagata.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('superficie-pagata.new', {
            parent: 'superficie-pagata',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie-pagata/superficie-pagata-dialog.html',
                    controller: 'SuperficiePagataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                supeDich: null,
                                supeAmmi: null,
                                supeRefr: null,
                                supeDete: null,
                                supeNsan: null,
                                supeAcce: null,
                                numTitoDich: null,
                                numTitoDete: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('superficie-pagata', null, { reload: true });
                }, function() {
                    $state.go('superficie-pagata');
                });
            }]
        })
        .state('superficie-pagata.edit', {
            parent: 'superficie-pagata',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie-pagata/superficie-pagata-dialog.html',
                    controller: 'SuperficiePagataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SuperficiePagata', function(SuperficiePagata) {
                            return SuperficiePagata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('superficie-pagata', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('superficie-pagata.delete', {
            parent: 'superficie-pagata',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/superficie-pagata/superficie-pagata-delete-dialog.html',
                    controller: 'SuperficiePagataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SuperficiePagata', function(SuperficiePagata) {
                            return SuperficiePagata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('superficie-pagata', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
