/**
 * Create a new asset
 * @param {org.ssu.workb.CreateNewPTJContract} asset
 * @transaction
 */
async function createNewPTJContract(asset) {
    let assetRegistry = await getAssetRegistry('org.ssu.workb.PTJContract');
    var factory = getFactory()

    num_id = (Math.floor(Math.random() * ( 999999 - 100000) + 100000)).toString(10)

    var assetID = asset.employeeName+asset.employerName + num_id;
    var newAsset = factory.newResource('org.ssu.workb', 'PTJContract', assetID)
    newAsset.company = asset.company;
    newAsset.employeeName = asset.employeeName;
    newAsset.employerName = asset.employerName;
    newAsset.employeePhoneNumber = asset.employeePhoneNumber;
    newAsset.employerPhoneNumber = asset.employerPhoneNumber;
    newAsset.year = asset.year;
    newAsset.month = asset.month;
    newAsset.day = asset.day;
    newAsset.stat = asset.stat;
    newAsset.wage = asset.wage;
    newAsset.workday = asset.workday;
    newAsset.workHour = asset.workHour;
    newAsset.period = asset.period;

    await assetRegistry.add(newAsset)
}
