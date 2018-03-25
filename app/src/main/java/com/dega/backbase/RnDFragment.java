package com.dega.backbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dega.backbase.model.EntriesResponse;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDFragment extends Fragment implements RnDContract.View {


    private RnDContract.Presenter presenter;


    public static RnDFragment newInstance() {
        RnDFragment fragment = new RnDFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.entries_fragment, container, false);
        rootView.findViewById(R.id.logoImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showDetailInNewView(null);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    @Override
    public void setPresenter(RnDContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showEntriesInList(EntriesResponse entriesResponse) {
//        vehiclesList.setVisibility(View.VISIBLE);
//        logoImageView.setVisibility(View.GONE);
//
//        LinearLayoutManager mLayoutManager = new
//                LinearLayoutManager(getActivity().getApplicationContext());
//        vehiclesList.setLayoutManager(mLayoutManager);
//
//        VehiclesAdapter adapterGrass = new
//                VehiclesAdapter(new ArrayList<>(vehiclesResponse.getVehicles()));
//
//        vehiclesList.setAdapter(adapterGrass);

    }

    @Override
    public void showErrorMessage(int message) {
//        logoImageView.setVisibility(View.VISIBLE);
//        vehiclesList.setVisibility(View.GONE);
//        Snackbar mySnackbar = Snackbar.make(logoImageView,
//                message, Snackbar.LENGTH_SHORT);
//        mySnackbar.setAction(R.string.try_again, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                presenter.loadEntries();
//            }
//        });
//
//        mySnackbar.show();
    }

    @Override
    public void showLastUpdate() {
//        Date date = new Date();
//        String stringDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
//        Snackbar.make(logoImageView,
//                getString(R.string.last_update, stringDate), Snackbar.LENGTH_LONG).show();
    }


    // The Adapter lives within the view since is the only class who access it
//    class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder> {
//
//        ArrayList<Vehicle> vehicles;
//
//        VehiclesAdapter(ArrayList<Vehicle> vehicles) {
//            this.vehicles = vehicles;
//        }
//
//        @Override
//        public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View root = LayoutInflater.from(getActivity().getApplicationContext()).
//                    inflate(R.layout.vehicle_list_item, parent, false);
//            return new VehicleViewHolder(root);
//        }
//
//        @Override
//        public void onBindViewHolder(VehicleViewHolder holder, int position) {
//            Vehicle vehicle = vehicles.get(position);
//            holder.setVehicleName(vehicle.getVrn());
//            holder.bind(vehicle, presenter);
//        }
//
//        @Override
//        public int getItemCount() {
//            return vehicles.size();
//        }
//
//        class VehicleViewHolder extends RecyclerView.ViewHolder {
//            TextView vehicleName;
//            LinearLayout vehicleItem;
//
//            VehicleViewHolder(View itemView) {
//                super(itemView);
//                this.vehicleName = itemView.findViewById(R.id.vehicleName);
//                this.vehicleItem = itemView.findViewById(R.id.vehicleItem);
//            }
//
//            void setVehicleName(String vehicleName) {
//                this.vehicleName.setText(vehicleName);
//            }
//
//            void bind(final Vehicle vehicle, final RnDContract.Presenter presenter) {
//                vehicleItem.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        presenter.showDetailInNewView(vehicle);
//                    }
//                });
//            }
//        }
//    }
}
